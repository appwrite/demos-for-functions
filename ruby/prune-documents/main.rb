require 'appwrite'
require 'date'

class PruneStaleDocument
  YEARS_TO_KEEP = 5
  DOCUMENTS_BATCH_SIZE = 100

  attr_reader :deleted_count

  def initialize
    @deleted_count = 0
  end

  def call
    collections.each do |collection|
      # skip collection if it has no attribute createdAt
      next unless collection['rules'].any? { |rule| rule['key'] == 'createdAt' }

      stale_documents(collection['$id']).each { |document| delete_document(document) }
    end
    puts deleted_count
  end

  private

  def collections
    database.list_collections.fetch('collections', [])
  end

  def stale_documents(collection_id)
    documents = []
    offset = 0
    loop do
      batch = database
                .list_documents(collection_id: collection_id,
                                filters: ["createdAt<#{min_time}"],
                                limit: DOCUMENTS_BATCH_SIZE,
                                offset: offset)
                .fetch('documents', [])
      break if batch.empty?

      documents += batch
      offset += DOCUMENTS_BATCH_SIZE
    end
    documents
  end

  def delete_document(document)
    database.delete_document(collection_id: document['$collection'], document_id: document['$id'])
    @deleted_count += 1
  end

  def database
    @database ||= Appwrite::Database.new(client)
  end

  def client
    @client ||= begin
      client = Appwrite::Client.new
      client.set_endpoint(ENV['APPWRITE_API_ENDPOINT']) # Your API Endpoint
            .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID']) # Your project ID available by default
            .set_key(ENV['APPWRITE_SECRET_KEY']) # Your secret API key
      client
    end
  end

  def min_time
    @min_time ||= begin
      current_date = Date.today
      Date.new(current_date.year - YEARS_TO_KEEP,
               current_date.month,
               current_date.day).to_time.to_i
    end
  end
end

PruneStaleDocument.new.call
